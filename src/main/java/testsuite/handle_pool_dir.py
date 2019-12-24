# 在pool文件夹中创建文件，删除文件。

import os

pool_dir = os.path.join(os.getcwd(), 'pool')

def creat_test_case_dirs():

	for index in range(16382):
		
		test_case_path = os.path.join(pool_dir, 'testcase' + str(index))

		if os.path.exists(test_case_path):
			continue
		else:
			os.mkdir(test_case_path)

creat_test_case_dirs()


