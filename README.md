# QueueTestTask

# Task Queue Service

Task Queue Service is an application that allows employers to add tasks to a queue and workers to retrieve and complete those tasks.

## Description

This application provides two roles: employer and worker. An employer can add tasks to the queue and view it. When adding a task, the employer can optionally assign it to a worker. A worker can retrieve tasks from the queue, mark them as completed, and view their list of active tasks.

## Installation and Launch

1. Clone the repository to your local machine.
2. Open the project in your IDE.
4. Run the application.

## Usage

1. Go to the employer's web page (`/employer`) to add tasks to the queue and view the queue.
2. Go to the worker's web page (`/worker`) to retrieve, complete, and view tasks.

## Endpoits 
- `POST /register` : For registration the worker
- `POST /authenticate` : For login the worker
- `POST /employer/addTask`: Add a task to the queue.
- `GET /employer/queue`: Get a list of all tasks in the queue.
- `GET /worker/getNextTask`: Get the next task for a worker.(authentication required)
- `GET /worker/{worker}/active-tasks`: Add a task to the queue (authentication required).
- `POST /worker/complete`: Mark a task as completed and remove it from the queue (authentication required).

## Dependencies

- Spring Boot
- Spring Security


