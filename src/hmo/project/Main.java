package hmo.project;

import java.io.File;
import java.io.IOException;

import hmo.project.input.InputFormatReader;
import hmo.project.output.OutputFormatWriter;
import hmo.project.state.State;

public class Main {
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2)
		{
			System.err.println("Usage: <inputfile> <outputfile>");
			return;
		}
		
		final InputFormatReader input = new InputFormatReader(new File(args[0]));
		
		final State state = input.ReadAll();
		
		final OutputFormatWriter output = new OutputFormatWriter(new File(args[1]));
		
		output.WriteAll(state);
	}
}
