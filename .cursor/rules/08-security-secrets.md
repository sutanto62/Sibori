---
description: Security & secrets
globs:
alwaysApply: false
---

- Never commit secrets, keystores, or `google-services.json`. Respect `.gitignore`.
- Avoid logging PII; scrub logs in production builds.