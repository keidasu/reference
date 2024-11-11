# Akita GPB modules
<br><br>

## Предварительные настройки

**1. Получить доступ в проектную область "АС DevOps" - OMNQA**

Доступ выдается через подачу СЗ в АСДД

**2. Установить Java JDK 17**

[Настройка Java для работы с локальными HTTPS-ресурсами](https://confluence.int.gazprombank.ru/pages/viewpage.action?pageId=26478786)

**3. Установить Intellij IDEA**

**4. Установить Git**
[Настройка Git для работы с HTTPS](https://confluence.int.gazprombank.ru/pages/viewpage.action?pageId=26478782)

**5. Необходимо скачать и установить следующие плагины для вашей версии Intellij IDEA :**

* Cucumber for Java
* Gherkin
* Lombok

**6. Настроить JDK:**
* в разделе *File -> Project Structure -> Project Settings -> Project -> Project SDK* выбрать Java 17
* в разделе *File -> Project Structure -> Platform Settings -> SDKs* выбрать Java 17
* в разделе *Build, Execution, Deployment -> Build tools -> Gradle -> Use Gradle from* выбрать 'gradle-wrapper.properties' file
* в разделе *Build, Execution, Deployment -> Build tools -> Gradle -> Gradle JVM* выбрать выбрать Java 17

**7. Настроить плагин Lombok:**

* в разделе *Build, Execution, Deployment -> Compiler -> Annotation Processors* требуется поставить галочку *Enable annotation processing*
* в разделе *Other settings -> Lombok plugin* требуется поставить галочку *Enable lombok plugin for this project*

**8. Настроить глобальные параметры gradle**
Нужно прописать в ${USER_HOME}/.gradle/gradle.properties следующие параметры:
```
mavenUser=gpbu????
mavenPassword=YOUR_PASSWORD
mavenUrl=https://nexus-ci.int.gazprombank.ru/repository/omnqa-maven/
```
Это позволяет держать пароли в сохранности и не коммитить их в репозиторий.

**9. [Статья в конфлюенс с дополнительной информацией](https://confluence.int.gazprombank.ru/display/DOPIT/BDD)**

## Code style
Используется плагин [spotless](https://github.com/diffplug/spotless) и [google-java-format](https://github.com/diffplug/spotless/tree/main/plugin-gradle#google-java-format)

## Contributing
Pull request-ы приветствуются!

### Branches
Используется [Git Feature Branch Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow)
* Последнее состояние всегда в master ветке
* feature/bugfix/hotfix разрабатываются в отдельных ветках созданных из master
* После завершения работы в отдельной ветке создается Pull requеst в master
* Когда Pull request готов к merge в мастер, его выполняет кто-то из [maintainers](#Maintainers)
* Релиз автоматически соберется и задеплоится после merge в мастер

### Versions
Используется подход [semver](https://semver.org/lang/ru/) и [плагин](https://github.com/ethauvin/semver-gradle)
Информация о версии хранится в versions.properties
для того что-бы поднять версию можно использовать gradle таски
incrementMajor - повысить мажорную версию
incrementMinor - повысить минорную версию
incrementPatch - повысить патч версию

### Tests
TBD

### Continius Integration
* В момент создания Pull Request запускатся [job](https://teamcity.int.gazprombank.ru/buildConfiguration/OMNQA_Akitagpb_AkitaGpbPr?) в TeamCity где проверятеся что сборка выполняется без ошибок
* После merge в мастер запускается [job](https://teamcity.int.gazprombank.ru/buildConfiguration/OMNQA_Akitagpb_AkitaGpbDeploy?) в TeamCity, который собирает jar и деплоит в Nexus

### Communication
Для коммуникаций существует канал в [Mattermost](https://chat/gpb/) AkitaGPB
Что-бы добавиться в канал, попросите кого-то из [maintainers](#Maintainers) или любого другого участника канала вас добавить

### Maintainers
Клевцов Александр Константинович <aleksandr.klevtsov@gazprombank.ru>
Сарников Илья Олегович <ilya.sarnikov@gazprombank.ru>
Солодков Андрей Александрович <andrey.solodkov@gazprombank.ru>
Суворов Дмитрий Александрович <dmitriy.a.suvorov@gazprombank.ru>
Губицкая Юлия Владимировна <yuliya.v.gubitskaya@gazprombank.ru>
Диканская Ирина Владимировна <irina.dikanskaya@gazprombank.ru>