# AI Agent Operational Guide

This document contains specific instructions for AI assistants to optimize their performance within the MealPlanner project.

## General Instructions
1.  **Read First**: Always read `ARCHITECTURE.md` and `CONTRIBUTING.md` before suggesting changes.
2.  **Modular Awareness**: Be aware of module boundaries. Do not add circular dependencies.
3.  **Tool Usage**: Use `find_declaration` to find existing implementations of similar features (e.g., `HomeScreen`) to use as templates.

## MVI Workflow for Agents
When asked to create a new feature:
1.  **Contract**: Start by defining the `Intent`, `ViewState`, and `SingleEvent` in a `contract` package.
2.  **ViewModel**: Implement the ViewModel inheriting from `BaseMviViewModel`.
3.  **UI Split**: Create a `Screen` (for logic) and a separate `Content` (for rendering).
4.  **Previews**: Ensure the `Content` and all sub-components have `@Preview` functions.
5.  **Navigation**: Register the new screen in the appropriate `NavigationGraph`.

## Feature Search
- To see how navigation is handled: Check `:presentation-core-navigation`.
- To see how styling is implemented: Check `:presentation-core-styling`.
- To see how widgets are built: Check `:presentation-core-ui`.
