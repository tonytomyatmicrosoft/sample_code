package com.example.demo;

import com.example.demo.model.VscodeExtension;
import com.example.demo.repository.VscodeExtensionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VscodeExtensionRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public DataInitializer(VscodeExtensionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Drop the 'vscode' table if it exists, then let Hibernate recreate it
        System.out.println(">>> Dropping 'vscode' table if it exists...");
        entityManager.createNativeQuery("DROP TABLE IF EXISTS vscode CASCADE").executeUpdate();

        // Recreate the table via Hibernate schema update
        entityManager.createNativeQuery(
            "CREATE TABLE vscode (" +
            "id BIGSERIAL PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "description VARCHAR(500), " +
            "publisher VARCHAR(100), " +
            "downloads INTEGER, " +
            "rating DOUBLE PRECISION)"
        ).executeUpdate();
        System.out.println(">>> Recreated 'vscode' table.");

        // Insert sample records
        repository.save(new VscodeExtension("Python", "Rich Python language support with IntelliSense, linting, and debugging", "Microsoft", 95000000, 4.7));
        repository.save(new VscodeExtension("Java Extension Pack", "Popular extensions for Java development in VS Code", "Microsoft", 25000000, 4.5));
        repository.save(new VscodeExtension("GitLens", "Supercharge Git within VS Code with blame annotations and more", "GitKraken", 32000000, 4.6));
        repository.save(new VscodeExtension("Prettier", "Opinionated code formatter supporting many languages", "Prettier", 42000000, 4.3));
        repository.save(new VscodeExtension("Docker", "Build, manage, and deploy containerized applications", "Microsoft", 28000000, 4.5));
        repository.save(new VscodeExtension("ESLint", "Integrates ESLint JavaScript linting into VS Code", "Microsoft", 38000000, 4.4));
        repository.save(new VscodeExtension("Live Server", "Launch a local development server with live reload", "Ritwick Dey", 45000000, 4.5));
        repository.save(new VscodeExtension("Thunder Client", "Lightweight REST API client for VS Code", "Thunder Client", 12000000, 4.6));

        System.out.println(">>> Inserted 8 sample records into the 'vscode' table.");
    }
}
