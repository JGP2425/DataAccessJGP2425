using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;


class Alumno
{
	private string nombre;
	private int nota;
	public string Nombre
	{
		get { return nombre; }
		set { nombre = value; }
	}
	public int Nota
	{
		get { return nota; }
		set
		{
			if (value >= 0 && value <= 10)
				nota = value;
		}
	}
	public Boolean Aprobado
	{
		get
		{
			if (nota >= 5)
				return true;
			else
				return false;
		}
	}
}

class Alumnos
{
	private ArrayList listaAlumnos = new ArrayList();

	// Agrega un nuevo alumno a la lista
	//        
	public void Agregar(Alumno alu)
	{
		listaAlumnos.Add(alu);
	}

	// Devuelve el alumno que está en la posición num
	//
	public Alumno Obtener(int num)
	{
		if (num >= 0 && num <= listaAlumnos.Count)
		{
			return ((Alumno)listaAlumnos[num]);
		}
		return null;
	}

	// Devuelve la nota media de los alumnos
	//
	public float Media
	{
		get
		{
			if (listaAlumnos.Count == 0)
				return 0;
			else
			{
				float media = 0;
				for (int i = 0; i < listaAlumnos.Count; i++)
				{
					media += ((Alumno)listaAlumnos[i]).Nota;
				}
				return (media / listaAlumnos.Count);
			}
		}
	}
}



