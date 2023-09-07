install:
	make -C app build

report:
	make -C app report
	
start:
	make -C app run

.PHONY: build
