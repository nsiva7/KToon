# Contributing to KToon

Thank you for your interest in contributing to KToon! 🎉

## 🚀 Quick Start

1. **Fork** the repository
2. **Clone** your fork: `git clone https://github.com/YOUR_USERNAME/KToon.git`
3. **Create** a branch: `git checkout -b feature/amazing-feature`
4. **Make** your changes
5. **Test** your changes: `./gradlew test`
6. **Commit** your changes: `git commit -m 'Add amazing feature'`
7. **Push** to your branch: `git push origin feature/amazing-feature`
8. **Open** a Pull Request

## 🎯 Types of Contributions

### 🐛 Bug Fixes
- Found a bug? Please check existing issues first
- Include reproduction steps and expected behavior
- Add tests that verify the fix

### ✨ New Features
- Check if the feature aligns with TOON format specification
- Discuss major features in an issue first
- Include comprehensive tests and documentation

### 📚 Documentation
- Improve README, code comments, or examples
- Fix typos or unclear explanations
- Add usage examples

### 🧪 Tests
- Improve test coverage
- Add edge case tests
- Performance benchmarks

## 🛠️ Development Setup

### Prerequisites
- **JDK 17+** (recommended: Temurin)
- **Git**

### Local Development
```bash
# Clone the repository
git clone https://github.com/nsiva7/KToon.git
cd KToon

# Run tests
./gradlew test

# Build the library
./gradlew build

# Generate documentation
./gradlew dokkaHtml
```

### Running Specific Tests
```bash
# All tests
./gradlew test

# Encoding tests only
./gradlew test --tests "KToonTest"

# Decoding tests only
./gradlew test --tests "*ToonDecoderTest*"
```

## 📋 Code Guidelines

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Keep functions focused and small
- Add KDoc for public APIs

### Testing
- Write tests for all new functionality
- Include both positive and negative test cases
- Test round-trip encoding/decoding
- Verify edge cases (empty objects, null values, etc.)

### Documentation
- Update README.md for new features
- Add KDoc comments for public APIs
- Include usage examples
- Update API reference if needed

## 🔄 TOON Format Compliance

KToon implements the [TOON format specification](https://github.com/toon-format/toon). When contributing:

- ✅ **DO**: Follow the official TOON specification
- ✅ **DO**: Maintain compatibility with other TOON implementations
- ✅ **DO**: Add Kotlin-specific enhancements (reified generics, null safety)
- ❌ **DON'T**: Break TOON format compatibility
- ❌ **DON'T**: Add non-standard TOON extensions

## 🚀 Release Process

Releases are automated via GitHub Actions:

1. Update version in `build.gradle.kts` (line 8)
2. Push to `main` branch
3. GitHub Action creates release automatically
4. JitPack builds the distribution
5. Website updates automatically

## 📝 Pull Request Guidelines

### Before Submitting
- [ ] Tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] Breaking changes clearly marked
- [ ] Version bumped if needed

### PR Title Format
- `feat: add new encoding option`
- `fix: handle null values in decoder`
- `docs: improve installation guide`
- `test: add round-trip validation`

### Description
Use the PR template to provide:
- Clear description of changes
- Type of change (feature, fix, docs, etc.)
- Testing information
- Related issues

## 🐛 Reporting Issues

### Bug Reports
Use the bug report template and include:
- Clear reproduction steps
- Expected vs actual behavior
- Environment details (Kotlin version, platform, etc.)
- Sample code that reproduces the issue

### Feature Requests
Use the feature request template and include:
- Clear description of the feature
- Use case and motivation
- Example usage code
- Impact on TOON format compatibility

## 💬 Community

- **Discussions**: [GitHub Discussions](https://github.com/nsiva7/KToon/discussions)
- **Issues**: [GitHub Issues](https://github.com/nsiva7/KToon/issues)
- **Email**: Feel free to reach out for major contributions

## 📄 License

By contributing to KToon, you agree that your contributions will be licensed under the MIT License.

## 🙏 Recognition

Contributors are recognized in:
- GitHub contributors list
- Release notes
- Project acknowledgments

Thank you for making KToon better! 🚀