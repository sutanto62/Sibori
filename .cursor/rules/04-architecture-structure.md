---
description: Architecture & structure 
globs:
alwaysApply: false
---

- Keep UI in Compose; avoid adding XML layouts unless strictly needed.
- Add new source under `app/src/main/java/id/or/sutanto/sibori/...` following package structure.
- Place new design tokens/typography in the theme files.
- If navigation is added, use official Navigation Compose; avoid custom back stacks unless justified.