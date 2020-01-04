package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo>, Observer {
    private String description;
    List<Todo> tasks;
    private Project project;
    private int hours;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //          the constructed project shall have no tasks.
    //          throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        hours = 0;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //          throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && !this.equals(task)) {
            tasks.add(task);
            task.addObserver(this);
            update(task, task.getEstimatedTimeToComplete());
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //          throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the sum of the estimated times it takes to complete
    //          each task in the project
    @Override
    public int getEstimatedTimeToComplete() {
//        int timeSoFar = 0;
//
//        if (tasks.size() == 0) {
//            return 0;
//        }
//
//        for (Todo child : tasks) {
//            timeSoFar = timeSoFar + child.getEstimatedTimeToComplete();
//        }
//
//        return timeSoFar;

        return hours;
    }


    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
//        return Collections.unmodifiableList(tasks);
        throw new UnsupportedOperationException();
    }


    @Override
    // EFFECTS: returns an integer between 0 and 100 which represents
    //          the percentage of completion (rounded down to the nearest integer).
    //          the value returned is the average of the percentage of completion of
    //          all the tasks and sub-projects in this project.
    public int getProgress() {

        int progressSum = 0;
        int numTasks = 0;

        if (tasks.size() == 0) {
            return 0;
        }

        for (Todo child : tasks) {
            progressSum = progressSum + child.getProgress();
            numTasks++;
        }

        return progressSum / numTasks;
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is
    //          completed, and false otherwise
    //          If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //          throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    // EFFECTS : returns new project iterator
    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }


    // EFFECTS : prints project's tasks' and subprojects' description in order of
    //           their priority from 1 to 4
    public void printProjectPriorities() {
        project = this;

        for (Todo t : project) {
            System.out.println(t.getDescription());

        }

    }

    @Override
    public void update(Observable o, Object arg) {
        hours = hours + (int) arg;
    }

    // allows for iteration over a project object
    private class ProjectIterator implements Iterator<Todo> {

        int iterVar = 0;
        int priority = 1;
        int count = 0;

        // EFFECTS: returns false if tasks have not all been returned
        @Override
        public boolean hasNext() {
            return (count < tasks.size());
        }



        // EFFECTS: returns next element in tasks with a given priority
        @Override
        public Todo next() {

            Todo toReturn = null;

            if (!(hasNext())) {
                throw new NoSuchElementException();
            }


            while (toReturn == null) {

                if (tasks.get(iterVar).getPriority().equals(new Priority(priority))) {
                    toReturn = tasks.get(iterVar);
                    count++;
                    iterVar++;
                } else {
                    iterVar++;
                }

                if (iterVar >= tasks.size()) {
                    priority++;
                    iterVar = 0;
                }
            }
            return toReturn;
        }
    }
}