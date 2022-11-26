# schoology-notifier (WIP)

Notifies users of upcoming Schoology assignment due dates thru Telegram. Currently a work-in-progress.


* Uses the [Schoology API](https://developers.schoology.com/api) and [rvanasa's Java implementation](https://github.com/rvanasa/schoology-api) to get all student courses, assignments and submissions.
* Uses [Gson](https://github.com/google/gson) to parse JSON API responses and stores data into User, Course, Assignment, and Submission Objects.
* (WIP)Stores data objects into local SQLite database with JDBC and uses Telegram as a user interface for simple cross-platform compatability.
