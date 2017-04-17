# East Games United game jam.

#### Become the king of the ocean!

Control your swarm of herrings! Eat the smaller fish, grow bigger. Be careful - the red ones are trying to stop you.

### About

Created in under 30 hours during the [East Games United](https://www.facebook.com/EastGamesUnited/) game jam by ~~3~~ 2 and half Java backend developers and one cute graphics designer using [KTX](https://github.com/czyzby/ktx). The jam's topic was "śledź" ("herring") and this _one_ of the games that we came up. The other one is [here](https://github.com/czyzby/egu-2016).

- Initial project structure, tips: [@czyzby](https://github.com/czyzby)
- Coding: [@Krzysiek](https://github.com/krzysiekruczaj), [@grafiszti](https://github.com/grafiszti)
- Graphics: [@marsza](https://github.com/marszaa)

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

