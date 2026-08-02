#!/bin/bash

# Ensure we are in the project root
GIT_DIR=$(git rev-parse --git-dir 2> /dev/null)

if [ -z "$GIT_DIR" ]; then
    echo "Error: Not a git repository."
    exit 1
fi

HOOK_DEST="$GIT_DIR/hooks/pre-commit"
HOOK_SOURCE="scripts/pre-commit"

echo "Installing git pre-commit hook..."

cp "$HOOK_SOURCE" "$HOOK_DEST"
chmod +x "$HOOK_DEST"

echo "Done! Git pre-commit hook installed to $HOOK_DEST"
