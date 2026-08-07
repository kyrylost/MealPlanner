# AI Agent Operational Guide

This document contains specific instructions for AI assistants to optimize their performance within the MealPlanner project.

## General Instructions
1.  **Read First**: Always read `ARCHITECTURE.md` and `CONTRIBUTING.md` before suggesting changes.
2.  **Modular Awareness**: Be aware of module boundaries. Do not add circular dependencies.
3.  **Tool Usage**: Use `find_declaration` and `find_files` to find existing implementations of similar features to use as templates.

## Standard Development Protocols

### 1. Creating a New Feature
When implementing a new screen or feature flow:
- **Contract**: Define `Intent`, `ViewState`, and `SingleEvent` in a dedicated `contract` package.
- **ViewModel**: Implement the ViewModel inheriting from `BaseMviViewModel`.
- **UI Split**: Create a `[Feature]Screen` (for logic/Koin) and a separate `[Feature]Content` (stateless rendering).
- **Previews**: Ensure the `Content` and all subcomponents have `@Preview` functions.
- **Navigation**: Register the new screen in the appropriate `NavigationGraph`.

### 2. Building UI Components
When creating or modifying UI elements:
- **Reusability Check**: BEFORE building a new widget, search `:presentation-core-ui` (using `find_files` or `grep`) to see if a similar component already exists. **Reuse or extend existing widgets instead of creating duplicates.**
- **Design Tokens**: Strictly use `Theme` tokens for colors, spacing, radius, and typography.
- **Previews**: Every top-level component and its variations must have `@Preview` functions.

### 3. Refactoring & Logic Updates
- **Impact Analysis**: Use `find_usages` to understand how your changes affect the rest of the codebase.
- **Constants**: Encapsulate logic-related constants in `companion object` blocks within the relevant class.
- **KDoc**: Provide documentation for all public classes and complex functions.

## Quick Reference: Module Roles
- **Navigation**: See `:presentation-core-navigation` for routing logic.
- **Styling**: See `:presentation-core-styling` for the design system and `Theme`.
- **Core UI**: See `:presentation-core-ui` for common widgets and icons.
- **Resources**: See `:core-localization` for all user-facing strings.

## Mandatory Validation
Before considering any task complete:
1.  **Sub-Agent Audit**: You MUST invoke a sub-agent using the `task` tool.
2.  **Audit Scope**: The sub-agent must review all modified files in the current session.
3.  **Audit Criteria**: Verify compliance with `AGENTS.md`, `ARCHITECTURE.md`, and `CONTRIBUTING.md`.
4.  **Reporting**: If the sub-agent finds violations (e.g., hardcoded strings, missing previews, logic errors), you must fix them before finishing.
