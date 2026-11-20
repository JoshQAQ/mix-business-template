#!/usr/bin/env python3
"""Render the Mix project template into a standalone Gradle project."""

import argparse
import os
from pathlib import Path
import re
import shutil
from zipfile import ZIP_DEFLATED, ZipFile


TEMPLATE = Path(__file__).resolve().with_name("template")
DEFAULT_PACKAGE = "org.example.mixdemo"
# Keep the local path exclusions aligned with template/.gitignore.
TEMPLATE_LOCAL_DIRECTORIES = {".gradle", ".idea", ".kotlin", ".omx", ".vscode", "build", "data", "out"}
TEMPLATE_LOCAL_FILES = {
    ".DS_Store",
    ".env",
    ".git",
    "Thumbs.db",
    "application-local.properties",
    "application-local.yaml",
    "application-local.yml",
    "local.properties",
}
PLACEHOLDER = re.compile(r"@@([A-Z_]+)@@")
PACKAGE_KEYWORDS = set("""
    as assert break case catch class const continue default do else enum extends false
    finally for fun goto if implements import in instanceof interface is new null
    object package return super switch this throw throws true try typealias typeof
    val var void when while
""".split())


def render(value, replacements):
    return PLACEHOLDER.sub(lambda match: replacements[match.group(1)], value)


def is_local_template_name(name):
    if name in TEMPLATE_LOCAL_FILES or name.endswith((".iml", ".log")):
        return True
    return name.startswith(".env.") and name != ".env.sample" and not name.endswith(".example")


def template_sources():
    for directory, subdirectories, filenames in os.walk(TEMPLATE):
        subdirectories[:] = [
            name for name in subdirectories
            if name not in TEMPLATE_LOCAL_DIRECTORIES and not is_local_template_name(name)
        ]
        for name in filenames:
            if is_local_template_name(name):
                continue
            source = Path(directory) / name
            if source.is_file():
                yield source


def create_archive(project, files):
    archive = project.with_suffix(".zip")
    stream = archive.open("xb")
    try:
        with stream, ZipFile(stream, "w", compression=ZIP_DEFLATED) as zipped:
            for file in files:
                zipped.write(file, file.relative_to(project.parent).as_posix())
    except BaseException:
        archive.unlink()
        raise
    return archive


def generate(project, replacements, zip_project):
    if TEMPLATE in project.parents:
        raise ValueError("choose an output directory outside the template tree")
    sources = sorted(template_sources())
    if not sources:
        raise FileNotFoundError(f"template is missing or empty: {TEMPLATE}")

    project.parent.mkdir(parents=True, exist_ok=True)
    project.mkdir()
    try:
        files = []
        for source in sources:
            relative_path = source.relative_to(TEMPLATE).as_posix()
            target = project / render(relative_path, replacements)
            target.parent.mkdir(parents=True, exist_ok=True)
            if source.suffix == ".jar":
                shutil.copyfile(source, target)
            else:
                contents = source.read_bytes().decode("utf-8")
                target.write_bytes(render(contents, replacements).encode("utf-8"))
            if relative_path == "gradlew":
                target.chmod(0o755)
            files.append(target)

        return create_archive(project, files) if zip_project else None
    except BaseException:
        shutil.rmtree(project)
        raise


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--name", required=True, help="project name, e.g. inventory-service")
    parser.add_argument("--package", default=DEFAULT_PACKAGE, help="base Kotlin package (default: %(default)s)")
    parser.add_argument("--group", help="Gradle group ID (defaults to --package)")
    parser.add_argument("--version", default="0.1.0-SNAPSHOT", help="project version (default: %(default)s)")
    parser.add_argument("--mix-version", default="1.3.3", help="Mix version (default: %(default)s)")
    parser.add_argument("--port", type=int, default=8080, help="default HTTP port (default: %(default)s)")
    parser.add_argument("--db-name", help="local MySQL database name (defaults to --name with underscores)")
    parser.add_argument("--output-dir", type=Path, default=Path("generated"), help="parent output directory (default: %(default)s)")
    parser.add_argument("--zip", action="store_true", help="also create a ZIP alongside the generated project")
    options = parser.parse_args()

    if not re.fullmatch(r"[a-z][a-z0-9]*(?:-[a-z0-9]+)*", options.name):
        parser.error("--name must start with a lowercase letter and use lowercase letters, digits, and hyphens")

    package_parts = options.package.split(".")
    if package_parts[0] == "kotlin" or any(
        not re.fullmatch(r"[a-z][a-z0-9_]*", part) or part in PACKAGE_KEYWORDS
        for part in package_parts
    ):
        parser.error("--package must contain valid lowercase Kotlin package segments")

    group = options.package if options.group is None else options.group
    if not re.fullmatch(r"[A-Za-z0-9_-]+(?:\.[A-Za-z0-9_-]+)*", group):
        parser.error("--group must be a dot-separated Maven group ID")
    for flag, version in (("--version", options.version), ("--mix-version", options.mix_version)):
        if not re.fullmatch(r"[A-Za-z0-9][A-Za-z0-9._+\-]*", version):
            parser.error(f"{flag} must be a valid version such as 1.0.0 or 1.0.0-SNAPSHOT")
    if not 1 <= options.port <= 65535:
        parser.error("--port must be between 1 and 65535")

    database_name = options.name.replace("-", "_") if options.db_name is None else options.db_name
    if not re.fullmatch(r"[a-z_][a-z0-9_]{0,63}", database_name):
        parser.error("use a --db-name of up to 64 lowercase letters, digits and underscores, starting with a letter or underscore")

    application_prefix = "".join(part.capitalize() for part in options.name.split("-"))
    replacements = {
        "APPLICATION_CLASS": f"{application_prefix}Application",
        "DATABASE_NAME": database_name,
        "GROUP_ID": group,
        "MIX_VERSION": options.mix_version,
        "PACKAGE_NAME": options.package,
        "PACKAGE_PATH": "/".join(package_parts),
        "PROJECT_NAME": options.name,
        "PROJECT_VERSION": options.version,
        "SERVER_PORT": str(options.port),
    }
    project = options.output_dir.expanduser().resolve() / options.name
    try:
        archive = generate(project, replacements, options.zip)
    except (OSError, ValueError) as error:
        parser.exit(1, f"生成项目失败：{error}\n")

    print(project)
    if archive is not None:
        print(archive)


if __name__ == "__main__":
    main()
