# Обработка изображений

## 1. Функционал приложения

Приложение для обработки изображений на сервере с использованием Java и OpenCV. Поддерживает загрузку изображений и выбор различных методов обработки, включая:

- **Линейное контрастирование** — изменяет контраст и яркость изображения на основе параметров `α` и `β`.
- **Локальная пороговая обработка**:
  - *Среднее значение* — порог рассчитывается как среднее значение пикселей в блоке.
  - *Гауссово разбиение* — порог рассчитывается на основе весов по Гауссу для каждого блока.
- **Адаптивная пороговая обработка** — использует локальное значение пикселя и константу `C` для определения порога.

## 2. Руководство пользователя

### Шаг 1: Загрузка изображения

1. Запустите приложение и загрузите изображение с помощью кнопки выбора файла в разделе «Выберите изображение».
2. Поддерживаются форматы изображений `.jpg`, `.png`, и `.bmp`.

### Шаг 2: Выбор метода обработки

1. В выпадающем списке **Метод обработки** выберите один из методов:
   - **Линейное контрастирование**
   - **Локальная пороговая (среднее)**
   - **Локальная пороговая (Гаусс)**
   - **Адаптивная пороговая**

2. При выборе метода **Линейное контрастирование** введите значения `α` и `β`.
3. Для методов пороговой обработки укажите **Размер блока** (нечетное число >1) и, при необходимости, **Константу C**.
   
### Шаг 3: Запуск обработки

1. Нажмите кнопку **Обработать изображение**.
2. После обработки результат появится в разделе «Результат» ниже формы.

---

## 3. Структура проекта

### Основные файлы и каталоги

- `src/main/java` — основная логика приложения.
  - `FileController.java` — контроллер для сервинга изображений из выходного каталога.
  - `ImageProcessingController.java` — контроллер для обработки изображений и передачи параметров на сервер.
  - `ImageProcessingService.java` — содержит методы обработки изображений с использованием OpenCV.

- `src/main/resources/static` — статические ресурсы.
  - `output/` — сохраняет обработанные изображения.
    
- `front` — ресурсы клиентской части.
  - `index.html` — интерфейс пользователя.
  - `style.css` — стили для страницы.
  - `script.js` — скрипты для взаимодействия с сервером.


### Основные компоненты

- **Контроллеры**: предоставляют API для загрузки изображений и методов обработки.
- **Сервисы**: обработка изображений с использованием OpenCV.
- **Фронтенд**: HTML, CSS и JavaScript для взаимодействия пользователя с сервером.

---

## 4. Руководство для программиста

### Требования

- Java 11 или выше
- Maven для сборки

### Установка

1. Клонируйте репозиторий:
   git clone https://github.com/hinnero/PKG_Lab_3.git
   
   cd PKG_Lab_3
   
Соберите проект с помощью Maven:

mvn clean install

### Запуск сервера
Для запуска выполните команду:

mvn spring-boot:run
