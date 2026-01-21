from collections import deque
import time

CAP_A = 4
CAP_B = 3
GOAL = 2

def draw_jug(name, water, cap):
    filled = "█" * water
    empty = "░" * (cap - water)
    return f"{name} | {filled}{empty} | {water} L"

def rules(A, B):
    r = []

    if A == 0:
        r.append(("Rule 1: Fill Jug A", (4, B)))
    if B == 0:
        r.append(("Rule 2: Fill Jug B", (A, 3)))
    if A == 4:
        r.append(("Rule 3: Empty Jug A", (0, B)))
    if B == 3:
        r.append(("Rule 4: Empty Jug B", (A, 0)))
    if A >= (3 - B) and B < 3:
        r.append(("Rule 9: Pour A → B", (A - (3 - B), 3)))
    if A < (3 - B) and A > 0:
        r.append(("Rule 10: Pour A → B", (0, A + B)))
    if B >= (4 - A) and A < 4:
        r.append(("Rule 11: Pour B → A", (4, B - (4 - A))))
    if B < (4 - A) and B > 0:
        r.append(("Rule 12: Pour B → A", (A + B, 0)))

    return r

def visual_simulation():
    visited = set()
    queue = deque()

    
    queue.append((0, 0, [("INITIAL STATE", (0, 0))]))

    while queue:
        A, B, path = queue.popleft()

        if (A, B) in visited:
            continue
        visited.add((A, B))

    
        if A == GOAL:
            print("\n--- VISUAL WATER JUG SIMULATION ---\n")
            for step, (rule, (x, y)) in enumerate(path):
                print(f"Step {step}: {rule}")
                print(draw_jug("Jug A", x, CAP_A))
                print(draw_jug("Jug B", y, CAP_B))
                print("-" * 30)
                time.sleep(1)
            print("✅ Goal Achieved!")
            return

        
        for rule, (newA, newB) in rules(A, B):
            if (newA, newB) not in visited:
                queue.append((newA, newB, path + [(rule, (newA, newB))]))

visual_simulation()
