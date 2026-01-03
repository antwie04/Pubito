package com.pubito.pubito_backend.services.permission_service;

import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("permissionService")
@RequiredArgsConstructor
public class PermissionService {

    private final UserRepository userRepository;
    private final BarRepository barRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final CompanyDetailsRepository companyDetailsRepository;

    public boolean canModifyBar(Long barId) {
        if (isAdmin()) return true;

        User me = currentUser();
        return barRepository.findById(barId)
                .map(b -> b.getOwner() != null && b.getOwner().getId().equals(me.getId()))
                .orElse(false);
    }

    public boolean canModifyMenu(Long menuId) {
        if (isAdmin()) return true;

        User me = currentUser();
        return menuRepository.findById(menuId)
                .map(m -> m.getBar() != null
                        && m.getBar().getOwner() != null
                        && m.getBar().getOwner().getId().equals(me.getId()))
                .orElse(false);
    }

    public boolean canModifyAddress(Long addressId) {
        if (isAdmin()) return true;

        User me = currentUser();
        return barRepository.findByAddressId(addressId)
                .map(b -> b.getOwner() != null && b.getOwner().getId().equals(me.getId()))
                .orElse(false);
    }

    public boolean canModifyCompanyDetails(Long companyDetailsId) {
        if (isAdmin()) return true;

        User me = currentUser();
        return barRepository.findByCompanyDetailsId(companyDetailsId)
                .map(b -> b.getOwner() != null && b.getOwner().getId().equals(me.getId()))
                .orElse(false);
    }

    public boolean canModifyReview(Long reviewId) {
        if (isAdmin()) return true;

        User me = currentUser();
        return reviewRepository.findById(reviewId)
                .map(r -> r.getUser() != null && r.getUser().getId().equals(me.getId()))
                .orElse(false);
    }

    private boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream().anyMatch(a -> "ADMIN".equals(a.getAuthority()));
    }

    private User currentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new RuntimeException("Unauthenticated");

        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}
