package dao;

import models.Trainer;

import java.util.ArrayList;

public interface ITrainer
{
	void addTrainer(String name, String password);
	void deleteTrainer(int id);
	ArrayList<Trainer> getAllTrainers();
}
