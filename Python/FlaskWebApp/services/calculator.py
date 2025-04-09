#
# Author: Rohtash Lakra
#


def add(x, y):
    return x + y


def subtract(x, y):
    return x - y


def multiply(x, y):
    return x * y


def divide(x, y):
    if y == 0:
        raise ValueError("Division by Zero!")

    return x / y


def calculator():
    while True:
        print("Select an operation:")
        print("1. Add")
        print("2. Subtract")
        print("3. Multiply")
        print("4. Divide")
        print()
        choice = int(input("Enter Choice:"))
        if choice in [1, 2, 3, 4]:
            x = float(input("Enter first number:"))
            y = float(input("Enter second number:"))
            match choice:
                case 1:
                    print(f"{x} + {y} = {add(x, y)}")
                case 2:
                    print(f"{x} - {y} = {subtract(x, y)}")
                case 3:
                    print(f"{x} * {y} = {multiply(x, y)}")
                case 4:
                    try:
                        print(f"{x} / {y} = {divide(x, y)}")
                    except Exception as e:
                        print(f"\n{str(e)}\n")
                case _:
                    print("Invalid Input!")

        print()
        ans = input("Continue Calculation (Y/N):")
        if ans.lower() != 'y':
            break
            # sys.exit(0)

    print("Goodbye!")


if __name__ == "__main__":
    calculator()
