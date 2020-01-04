# Pomo-todo-app

This is a school project that is meant to be the basic implementation of a to-do list app. 
It was created in Intellij, but should run in any IDE that allows you to execute files. 
To test out the app, run the POMOTodo-App in the ui folder. ConsoleTodoApp is the console version of the app.

When adding a new to-do list item, write the description in the following format: 

"description" ## "tags separated by semicolons"

For example the description:

buy milk ## today; important; grocery store

will set the description of the task to buy milk, the due date to the current date, the priority to important, and give the
task the tag #grocery store. The due date can be parsed out if you enter a tag as today or tomorrow, otherwise you can set 
the due date to any due date in the future by editing the task. If urgent or important are added as a tag, the task will be
marked with the corresponding priority (as both urgent and important if both tags are added, but neither if neither is added).
The status of the task can be in progress, done, to do, or up next. The most recent tag typed that matches any of those will be 
added to the task as its status. All other tags that do not match the due date, status, or priority will become their own tags.
