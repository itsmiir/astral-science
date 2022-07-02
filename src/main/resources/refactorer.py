# import required module
import os
# assign directory
directory = 'data/astralscience'
 
# iterate over files in
# that directory
def check(f):
        # checking if it is a file
        if os.path.isdir(f):
            for filename2 in os.listdir(f):
                f2 = os.path.join(f, filename2)
                if os.path.isdir(f2):
                    check(f2)
                if os.path.isfile(f2):
                    with open(f2, "r") as file:
                        try:
                            filedata = file.read()

                            # Replace the target string
                            filedata = filedata.replace('astral_science', 'astralscience')
                            filedata = filedata.replace('ntrstlr', 'astralscience')
                            with open(f2, 'w') as file:
                                file.write(filedata)
                            with open(f2, "r") as file:
                                for line in file:
                                    print(line)

                        except UnicodeDecodeError as e:
                            pass
                        

                        # Write the file out again
                    

for filename in os.listdir(directory):
    f = os.path.join(directory, filename)
    check(f)