package com.onlinecodecompiler.code_execution_service.service;


import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CodeExecutionService {

    public void executeCode(String languageName, String code)
    {
        executePythonCode(code);
    }
    private void executePythonCode(String code)
    {
        String codeFileName = "user_code.py";
        try {
            File codeFile = new File(codeFileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile))) {
                writer.write(code);
            }

            // Step 2: Run the code file in a Docker container
            Process process = new ProcessBuilder(
                    "docker", "run", "--rm",
                    "-v", codeFile.getAbsolutePath() + ":/usr/src/app/user_code.py",  // Mount the file into Docker container
                    "python:3.8", "python", "/usr/src/app/user_code.py") // Execute the file
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();

            System.out.println(exitCode);
            System.out.println(output.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
