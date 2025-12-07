Roommate Expense Splitter (Java Desktop Application)

📌 Project Overview

The Roommate Expense Splitter is a Java-based desktop application designed to help roommates fairly manage and split shared expenses such as rent, groceries, utilities, and subscriptions. The application allows users to enter group expenses, automatically calculates balances, and determines who owes whom; all with persistent file storage and a clean graphical user interface.

This project is being developed as a real-world portfolio application to demonstrate object-oriented programming, file persistence, financial computation logic, and GUI-based event-driven programming in Java.

🎯 Project Goals

Provide a simple and reliable way for roommates to manage shared expenses

Automatically calculate balances and debts

Persist all data locally using file storage

Support multiple roommates and unlimited expenses

Deliver a clean and user-friendly desktop GUI

🛠 Planned Features

Core Backend Logic

Add and remove roommates

Add new expenses with:

Description

Amount

Payer

Participants

Automatic balance calculation

Debt simplification (who owes who)

Data validation

Save/load data from file

Graphical User Interface (JavaFX)

Main dashboard with balance overview

Add expense form

Expense history view

Roommate management panel

Responsive window resizing

Data Persistence

Local file-based storage

Automatic loading on startup

Safe saving on exit

🧱 Planned Architecture

This project follows a clean layered design:

[ JavaFX GUI ]
        ↓
[ Expense Management Logic ]
        ↓
[ File Persistence Layer ]

This ensures:

Separation of concerns

Easier testing

Clean future expansion

🗂 Planned File Structure
roommate-expense-splitter/
│
├── src/
│   ├── model/
│   │   ├── Roommate.java
│   │   └── Expense.java
│   │
│   ├── service/
│   │   └── ExpenseManager.java
│   │
│   ├── storage/
│   │   └── FileManager.java
│   │
│   └── ui/
│       └── MainApp.java
│
├── data/
│   └── expenses.txt
│
├── README.md
└── .gitignore

🚀 Development Roadmap

Phase 1 — Backend Logic (Console-Based)

Implement all core business logic

Implement save/load functionality

Fully test expense splitting logic

Phase 2 — JavaFX GUI Integration

Create responsive desktop UI

Connect GUI to backend logic

Add dynamic updates and error handling

Phase 3 — Refinement & Polish

Improve UI layout

Add edge-case validation

Optimize file format

Add testing where applicable

🧠 Skills Demonstrated

Object-Oriented Programming (OOP)

Data Structures & Algorithms

File Input/Output

Event-Driven Programming

JavaFX GUI Development

Financial & Business Logic Modeling

Git & GitHub Version Control

✅ Current Status

✔ Repository Initialized
⬜ Backend Logic In Progress
⬜ GUI Development
⬜ Final Testing

👨‍💻 Author

Developed by Kris Bali
Computer Science Undergraduate | Software Engineering Focus
