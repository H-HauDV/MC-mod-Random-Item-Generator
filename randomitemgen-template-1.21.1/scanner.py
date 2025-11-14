import os

# === CONFIG ===
ROOT_FOLDER = "."  # Root folder of your mod project
OUTPUT_FILE = "mod_code_scan.txt"
EXTENSIONS = (".java")  # File types to scan

# === FUNCTION TO SCAN FILES ===
def scan_files(root_folder, extensions):
    file_data = []
    for dirpath, _, filenames in os.walk(root_folder):
        for file in filenames:
            if file.endswith(extensions):
                file_path = os.path.join(dirpath, file)
                try:
                    with open(file_path, "r", encoding="utf-8") as f:
                        content = f.read()
                except Exception as e:
                    content = f"[ERROR READING FILE: {e}]"
                file_data.append((file_path, content))
    return file_data

# === WRITE TO TXT ===
def write_to_file(file_data, output_file):
    with open(output_file, "w", encoding="utf-8") as f:
        for path, content in file_data:
            f.write(f"--- FILE: {path} ---\n")
            f.write(content)
            f.write("\n\n")
    print(f"Scan complete! Output saved to {output_file}")

# === MAIN ===
if __name__ == "__main__":
    files = scan_files(ROOT_FOLDER, EXTENSIONS)
    write_to_file(files, OUTPUT_FILE)
