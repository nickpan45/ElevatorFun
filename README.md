# Elevator Simulation System

## Overview
This project is a **multi-threaded Elevator Simulation System** that models real-world elevator operations in a **12-floor building** with **4 elevators** handling **random requests from people**. The system ensures:
- **Efficient elevator selection** using **priority-based movement**.
- **Thread-safe concurrent execution** with `ReentrantLock` and `ExecutorService`.
- **Security enforcement** for **restricted floors (9-12)**.
- **Dynamic request handling** to optimize elevator movement.
  
## Features
✔ **Multi-threaded execution** for real-time elevator operations.  
✔ **Security access enforcement** for floors **9-12**.  
✔ **Priority-based elevator selection** ensuring efficient movement.  
✔ **Real-time status display** of elevators and pending requests.  
✔ **Automatic shutdown of elevators** when simulation ends.  

## Technologies Used
- **Java** (Core Multi-threading, Concurrency)
- **ExecutorService** (Thread Pool Execution)
- **ReentrantLock** (Thread-safe operations)
- **ScheduledExecutorService** (Timed movement simulation)

## How It Works
1. **Initialize 4 Elevators** with **random starting floors**.
2. **Generate 8 people** requesting elevators **at random intervals**.
3. **Assign best elevator** based on:
   - **Proximity** (closest available elevator).
   - **Security access** (restricted floors validation).
4. **Simulate movement** and **log events** dynamically.
5. **Shutdown elevators** when the simulation ends.

## Installation & Running the Simulation
### **Steps**
1. **Clone the repository**:
   --> bash
   https://github.com/nickpan45/ElevatorFun.git
   cd elevator-simulation
