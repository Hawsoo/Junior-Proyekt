package me.hawsoo.util.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.util.vector.Vector3f;

/**
 * Manages OBJ models.
 * @author Administrator
 *
 */
public class ObjModel
{
	
	/**
	 * Loads an OBJ model using a file.
	 * @param file - the file
	 * @return the created model
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Model loadModel(File file) throws FileNotFoundException, IOException
	{
		return createModel(new BufferedReader(new FileReader(file)));
	}
	
	/**
	 * Loads an OBJ model using an input stream.
	 * @param in - the input stream
	 * @return the created model
	 * @throws IOException
	 */
	public static Model loadModel(InputStream in) throws IOException
	{
		return createModel(new BufferedReader(new InputStreamReader(in, "UTF-8")));
	}
	
	/**
	 * Creates the actual model.
	 * @param bReader - a bufferedreader associated with the location of the OBJ model
	 * @return the created model
	 * @throws IOException
	 */
	private static Model createModel(BufferedReader bReader) throws IOException
	{
		// Load components of OBJ
		Model model = new Model();
		String currentLine;
		while((currentLine = bReader.readLine()) != null)
		{
			// Add Vertices
			if (currentLine.startsWith("v "))
			{
				float x = Float.valueOf(currentLine.split(" ")[1]);
				float y = Float.valueOf(currentLine.split(" ")[2]);
				float z = Float.valueOf(currentLine.split(" ")[3]);
				
				model.vertices.add(new Vector3f(x, y, z));
			}
			// Add Vertex Normals
			else if (currentLine.startsWith("vn "))
			{
				float x = Float.valueOf(currentLine.split(" ")[1]);
				float y = Float.valueOf(currentLine.split(" ")[2]);
				float z = Float.valueOf(currentLine.split(" ")[3]);
				
				model.normals.add(new Vector3f(x, y, z));
			}
			// Add faces
			else if (currentLine.startsWith("f "))
			{
				String[] indexX = currentLine.split(" ")[1].split("/");
				String[] indexY = currentLine.split(" ")[2].split("/");
				String[] indexZ = currentLine.split(" ")[3].split("/");
				
				// Vertices
				model.includeVerts = true;
				Vector3f vertexIndices =
						new Vector3f(
								Float.valueOf(currentLine.split(" ")[1].split("/")[0]),
								Float.valueOf(currentLine.split(" ")[2].split("/")[0]),
								Float.valueOf(currentLine.split(" ")[3].split("/")[0]));
				
				// Texture Vertices
				
				
				// Normals
				Vector3f normalIndices = null;
				if (indexX.length >= 3 && indexY.length >= 3 && indexZ.length >= 3)
				{
					model.includeNorms = true;
					normalIndices =
					new Vector3f(
							Float.valueOf(currentLine.split(" ")[1].split("/")[2]),
							Float.valueOf(currentLine.split(" ")[2].split("/")[2]),
							Float.valueOf(currentLine.split(" ")[3].split("/")[2]));
				}
				
				model.faces.add(new Face(vertexIndices, normalIndices));
			}
		}
		
		bReader.close();
		
		// Prepare the model for VBO drawing
		model.prepare();
		
		return model;
	}
}
