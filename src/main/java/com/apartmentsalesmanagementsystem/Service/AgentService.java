package com.apartmentsalesmanagementsystem.Service;

import com.apartmentsalesmanagementsystem.Entity.Agent;
import com.apartmentsalesmanagementsystem.Repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    public Agent saveAgent(Agent agent) {
        // Enforce email domain restriction
        if (agent.getEmail() == null || !agent.getEmail().endsWith("@agent.com")) {
            throw new IllegalArgumentException("Agent emails must end with @agent.com");
        }
        if (agent.getActive() == null) {
            agent.setActive(true);
        }
        return agentRepository.save(agent);
    }
}