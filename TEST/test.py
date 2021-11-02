import sys
output = sys.argv[1]
expected = sys.argv[2]
correct = True
print("%-20s %-20s"%("Output","Expected"))
print("="*30)
with open(output) as output_file:
	with open(expected) as expected_out:
		x = output_file.readlines()
		y = expected_out.readlines()
		for i in range (len(y)):
			try:
				if(x[i].strip() != y[i].strip()):
					correct = False
					print(">%-20s >%-20s"%(x[i].strip(),y[i].strip()))
				else:
					print("%-20s %-20s"%(x[i].strip(),y[i].strip()))
			except IndexError:
				correct = False
				print("%-20s %-20s"%("> ",y[i].strip()))
				continue
ac = lambda a: "AC" if correct else "WA"
print("="*30)
print("TEST "+output[19]+":"+ac(correct))
print("="*30)
