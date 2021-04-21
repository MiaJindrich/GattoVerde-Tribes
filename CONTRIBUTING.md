# Contributing to the project

## Restricted branches

- `develop` - all the implemented features which are done and deployed
- `master` - stable version deployed

## Workflow

1. Create a feature branch when you start to work on a story and commit your
changes to this. Suggestions: The branch name should match the JIRA ticket
that you are working on that branch.
1. Commit message format should be: `<JIRA ticket ID> <JIRA ticket summary>`
1. Push this frequently to the remote repository from your local
1. When the feature is done, create a Pull Request from
the `feature_branch` to `develop`, follow the guidelines.
1. When the PR is approved, merge it, and delete your feature branch

## Git Commit Guidelines

Read this article how to write meaningful commit messages:
[How to Write a Git Commit Message](https://xkcd.com/1296/)

## Pull Request guidelines

- From `feature_branch` to `develop`: add all developers
and PM as reviewers, 3 approves needed for merging, one of them
must be a code owner
- From `develop` to `master`: this is managed by the PM
