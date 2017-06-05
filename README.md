# East Games United game jam

#### Become the king of the ocean!

Control your swarm of herrings! Eat the smaller fish, grow bigger. Be careful - the red ones are trying to stop you.

![Menu](.github/sledz0.png)
![Game](.github/sledz1.png)
![Loss](.github/sledz2.png)

### About

Created in under 30 hours using [KTX](https://github.com/czyzby/ktx) during the [East Games United](https://www.facebook.com/EastGamesUnited/) game jam by ~~three~~ two and half Java backend developers and one cute graphics designer. The jam's topic was _"śledź"_ (_"herring"_) and this _one_ of the games that we came up. The other one is [here](https://github.com/czyzby/egu-2016). We ended up on the third place, which isn't that bad.

- Coding: [@krzysiek](https://github.com/krzysiekruczaj), [@grafiszti](https://github.com/grafiszti)
- Graphics: [@marsza](https://github.com/marszaa)
- Initial project structure, tips: [@czyzby](https://github.com/czyzby)

**Note**: we were desperate and sleep deprived. This ain't your Uncle Bob coding exercise. Don't judge us by this code.

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies. Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands. Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `desktop:jar`: builds application's runnable jar, which can be found at `desktop/build/libs`.
- `desktop:run`: starts the application.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

