/*
 * You can use the following import statements
 *
 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 * 
 */

// Write your code here
package com.example.eventmanagementsystem.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eventmanagementsystem.model.*;
import java.util.*;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Integer> {
   
}
