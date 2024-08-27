package morefiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

//This program simulates an online coding academy, generating random students with random demographics
//assigns them courses and has the students "watch" them.
//It outputs a file in the same directory that this was executed in with the data formatted for JSON files
public class Main
{
	public static void main(String[] args)
	{
		Course rmc = new Course("RMC", "Ruby Masterclass", 50);
		Course nmc = new Course("NMC", "NodeJS Masterclass", 50);
		File file = new File(".\\studentData.json");

		List<String> students = Stream.generate(() ->
						Student.populateNewRandomStudent(rmc, nmc))
				.limit(10)
				.map(Student::toJSON)
				.toList();

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)))
		{
			boolean firstLine = true;
			bw.write("[");
			for(String s : students)
			{
				if(!firstLine)
				{
					bw.write(", ");
					bw.newLine();
				}
				bw.write(s);
				firstLine = false;
			}
			bw.write("]");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
