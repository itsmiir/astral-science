# import required module
import os
# assign directory
directory = 'data/astralscience'
 
# iterate over files in
# that directory
def check(f):
    if not os.path.isdir(f):
        return
    for filename2 in os.listdir(f):
        f2 = os.path.join(f, filename2)
        if os.path.isdir(f2):
            return check(2)
        with open(f2, 'w+') as file:
            try:
                filedata = file.read().replace('astral_science', 'astralscience').replace('ntrstlr', 'astralscience')
                file.write(filedata)
                [print line for line in file.readlines()]
            except UnicodeDecodeError as _:
                pass

for filename in os.listdir(directory):
    f = os.path.join(directory, filename)
    check(f)
