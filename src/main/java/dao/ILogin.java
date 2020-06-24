package dao;

import models.Trainer;

public interface ILogin
{
	Trainer login(String name, String pass);
}
