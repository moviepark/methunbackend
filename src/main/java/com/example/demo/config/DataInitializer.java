package com.example.demo.config;

import com.example.demo.model.CampaignAccount;
import com.example.demo.model.OutreachEvent;
import com.example.demo.model.VoterProfile;
import com.example.demo.model.VolunteerShift;
import com.example.demo.repository.CampaignAccountRepository;
import com.example.demo.repository.OutreachEventRepository;
import com.example.demo.repository.VoterProfileRepository;
import com.example.demo.repository.VolunteerShiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(CampaignAccountRepository accountRepository,
                               OutreachEventRepository eventRepository,
                               VoterProfileRepository voterRepository,
                               VolunteerShiftRepository shiftRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            createAccount(accountRepository, passwordEncoder, "director@votesphere.org", "Campaign Director", "CAMPAIGN_DIRECTOR", "9000000001");
            createAccount(accountRepository, passwordEncoder, "organizer@votesphere.org", "Field Organizer", "FIELD_ORGANIZER", "9000000002");
            createAccount(accountRepository, passwordEncoder, "volunteer@votesphere.org", "Outreach Volunteer", "OUTREACH_VOLUNTEER", "9000000003");
            createAccount(accountRepository, passwordEncoder, "auditor@votesphere.org", "Compliance Auditor", "COMPLIANCE_AUDITOR", "9000000004");

            if (eventRepository.count() == 0) {
                OutreachEvent event = new OutreachEvent();
                event.setEventTitle("North District Town Hall");
                event.setEventType(OutreachEvent.EventType.TOWN_HALL);
                event.setLocation("North District Community Center");
                event.setScheduledDate(LocalDateTime.now().plusDays(7));
                event.setTargetCapacity(120);
                event.setCurrentRegistrations(0);
                event.setEventStatus(OutreachEvent.EventStatus.PLANNED);
                OutreachEvent savedEvent = eventRepository.save(event);

                VolunteerShift shift = new VolunteerShift();
                shift.setEvent(savedEvent);
                shift.setStartTime(LocalDateTime.now().plusDays(7).withHour(10).withMinute(0));
                shift.setEndTime(LocalDateTime.now().plusDays(7).withHour(13).withMinute(0));
                shift.setShiftStatus(VolunteerShift.ShiftStatus.OPEN);
                shiftRepository.save(shift);
            }

            if (voterRepository.count() == 0) {
                VoterProfile voter = new VoterProfile();
                voter.setFirstName("Asha");
                voter.setLastName("Kumar");
                voter.setElectoralDistrict("North District");
                voter.setContactNumber("9000011111");
                voter.setSupportScore(50);
                voter.setEngagementStatus(VoterProfile.EngagementStatus.UNCONTACTED);
                voterRepository.save(voter);
            }
        };
    }

    private void createAccount(CampaignAccountRepository repository,
                               PasswordEncoder passwordEncoder,
                               String email,
                               String fullName,
                               String role,
                               String phone) {
        if (repository.existsByEmail(email)) return;
        CampaignAccount account = new CampaignAccount();
        account.setEmail(email);
        account.setPasswordHash(passwordEncoder.encode("password123"));
        account.setFullName(fullName);
        account.setDomainRole(role);
        account.setPhoneNumber(phone);
        account.setActive(true);
        repository.save(account);
    }
}
