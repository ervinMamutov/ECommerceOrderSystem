#!/bin/bash

# Conversation Logging Script for Claude Code
# Automatically saves conversation history to markdown files
# Full conversation mode: logs user messages, Claude responses, and tool executions

EVENT_TYPE="$1"
TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")
SESSION_TIMESTAMP=$(date "+%Y%m%d-%H%M%S")

# Determine script directory and log directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_DIR="$SCRIPT_DIR/../conversations"

# Create log directory if it doesn't exist
mkdir -p "$LOG_DIR"

# Session file: one file per session
# Store the session log file path in a temp file so all hooks in this session use the same file
SESSION_FILE_PATH="/tmp/claude-session-log-$$"
if [ -f "$SESSION_FILE_PATH" ]; then
    LOG_FILE=$(cat "$SESSION_FILE_PATH")
else
    LOG_FILE="$LOG_DIR/conversation-$SESSION_TIMESTAMP.md"
    echo "$LOG_FILE" > "$SESSION_FILE_PATH"
fi

case "$EVENT_TYPE" in
    SessionStart)
        # Initialize markdown file with header
        cat > "$LOG_FILE" << EOF
# Conversation Log
**Date:** $TIMESTAMP
**Project:** E-commerce Order System
**Location:** \`$(pwd)\`

---

EOF
        ;;

    UserPromptSubmit)
        # Log user input from stdin
        USER_INPUT=$(cat)
        cat >> "$LOG_FILE" << EOF
## User [$TIMESTAMP]

$USER_INPUT

EOF
        ;;

    PostToolUse)
        # Log tool execution details (full detail mode)
        TOOL_DATA=$(cat)

        # Extract tool info using jq if available
        if command -v jq &> /dev/null; then
            TOOL_NAME=$(echo "$TOOL_DATA" | jq -r '.tool_name // "Unknown"')

            cat >> "$LOG_FILE" << EOF
### Tool: $TOOL_NAME [$TIMESTAMP]

<details>
<summary>Tool Input</summary>

\`\`\`json
$(echo "$TOOL_DATA" | jq -r '.tool_input // {}' | jq .)
\`\`\`

</details>

<details>
<summary>Tool Output</summary>

\`\`\`
$(echo "$TOOL_DATA" | jq -r '.tool_output // "No output"')
\`\`\`

</details>

EOF
        else
            # Fallback if jq is not available
            cat >> "$LOG_FILE" << EOF
### Tool Execution [$TIMESTAMP]

<details>
<summary>Details</summary>

\`\`\`
$TOOL_DATA
\`\`\`

</details>

EOF
        fi
        ;;

    SessionEnd)
        # Finalize the log
        cat >> "$LOG_FILE" << EOF

---

**Session Ended:** $TIMESTAMP

EOF
        # Clean up temp file
        rm -f "$SESSION_FILE_PATH"
        ;;
esac

exit 0
