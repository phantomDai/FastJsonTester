import os

testing_version = 'v31'
add_content = ' mutants.' + testing_version + '.'

# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "annotation")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "asm")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "parser")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "parser", "deserializer")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "serializer")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "config")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "hsf")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "moneta")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "jaxrs")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "retrofit")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "spring")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "support", "springfox")
# parent_path = os.path.join(os.getcwd(), testing_version, "com", "alibaba", "fastjson", "util")

def refactor_import_content():
	# temp_path = os.path.join(parent_path, "fastjson_1_2_32", "com", "alibaba", "fastjson", "JSON.java")

	file_names = os.listdir(parent_path)

	for name in file_names:
		temp_path = os.path.join(parent_path, name)
		if os.path.isdir(temp_path):
			continue
		else:
			content = ''
			with open(temp_path, 'r', encoding="utf-8") as file:
				for aline in file.readlines():
					# 改变import的内容
					if aline.find('import com.alibaba') == 0:
						aline = aline.replace('import com.alibaba', 'import' + add_content + 'com.alibaba')
						content += aline
					elif aline .find('import static com.alibaba') == 0:
						aline = aline.replace('import static com.alibaba', 'import static' + add_content + 'com.alibaba')
						content += aline
					else:
						content += aline
			file.close()
			writeJavaFile(temp_path, content)
			content = ''

def writeJavaFile(path, content):
	file = open(path, 'w+')
	file.writelines(content)
	file.close()

# refactor_import_content()


def refactor_common_sentence():
	target_path = os.path.join(os.getcwd(), testing_version,
	 "com", "alibaba", "fastjson","parser", "deserializer","ASMDeserializerFactory.java")

	content = ''
	with open(target_path, 'r', encoding='utf-8') as file:
		for aline in file.readlines():
			if 'import' not in aline and 'com.alibaba' in aline and 'package' not in aline:
				aline = aline.replace('com.alibaba', 'mutants.' + testing_version + '.com.alibaba')
				content += aline
			else:
				content += aline
	file.close()

	file = open(target_path, 'w', encoding='utf-8')
	file.writelines(content)
	file.close()

refactor_common_sentence()



