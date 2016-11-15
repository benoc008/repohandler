package tv.ustream.logic;

import tv.ustream.domain.Repository;

import java.util.Date;

public class RepositoryBuilder {

    private Repository repository;

    public RepositoryBuilder() {
        repository = new Repository();
        repository.setAccessCounter(0L);
        repository.setCreationTime(new Date());
    }

    public RepositoryBuilder withName(String name){
        repository.setName(name);
        return this;
    }

    public RepositoryBuilder withCreator(String creator){
        repository.setCreator(creator);
        return this;
    }

    public Repository build(){
        return repository;
    }
}
