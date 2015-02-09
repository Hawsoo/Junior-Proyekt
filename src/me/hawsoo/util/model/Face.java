package me.hawsoo.util.model;

import org.lwjgl.util.vector.Vector3f;

/**
 * Holds the properties of one face.
 * @author Administrator
 *
 */
public class Face
{
	public Vector3f vertex;		// Holds three indexes instead of vertices or normals (for reference)
	public Vector3f normal;		// Holds three indexes instead of vertices or normals (for reference)
	
	public Face(Vector3f vertex, Vector3f normal)
	{
		this.vertex = vertex;
		this.normal = normal;
	}
}
