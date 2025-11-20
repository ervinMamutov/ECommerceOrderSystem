# Claude Code Configuration

This directory contains configuration and scripts for Claude Code.

## Automatic Conversation Logging

This project is configured to automatically save all Claude Code conversations to markdown files.

### Configuration

- **Location:** `.claude/conversations/`
- **Format:** One file per session (`conversation-YYYYMMDD-HHMMSS.md`)
- **Detail Level:** Full conversation (user messages, Claude responses, tool calls, and results)

### Files

- **hooks/save-conversation.sh** - Bash script that captures conversation data
- **settings.json** - Hook configuration that triggers the logging script
- **conversations/** - Directory where conversation logs are saved (git-ignored)

### How It Works

The logging system uses Claude Code's hook system to capture conversation events:

1. **SessionStart** - Creates a new markdown file with header information
2. **UserPromptSubmit** - Logs each user description with timestamp
3. **PostToolUse** - Logs all tool executions with inputs and outputs
4. **SessionEnd** - Finalizes the log file

### Viewing Conversation Logs

All conversations are saved in `.claude/conversations/`:

```bash
ls -lah .claude/conversations/
cat .claude/conversations/conversation-20251119-143022.md
```

### Privacy Note

Conversation logs are automatically excluded from git commits via `.gitignore` to prevent accidental exposure of sensitive information.

### Customization

To modify the logging behavior, edit:
- `.claude/hooks/save-conversation.sh` - Change what gets logged
- `.claude/settings.json` - Change when logging occurs

For more information about Claude Code hooks, visit: https://code.claude.com/docs/en/hooks.md
