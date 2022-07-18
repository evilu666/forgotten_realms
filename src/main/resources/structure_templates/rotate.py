import sys
with open(sys.argv[1], 'r') as f:
    lines = list(f.readlines())
    columns = []
    col_size = 0
    for line in lines:
        col = []
        for c in line:
            col.append(c)
        columns.append(col)
        col_size = len(line)

    for i in range(col_size):
        for col in columns:
            print(col[i], end="")
        print("")
