package com.jgp.OnlineMarket.OnlineMarket.services;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.ISellerEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellersServiceTest {

    @Mock
    private ISellerEntityDAO sellerDAO;

    @InjectMocks
    private sellersService sellersService;

    private SellerEntity seller;

    @BeforeEach
    void createMockupUser() {
        seller = new SellerEntity();
        seller.setCif("D45678901");
        seller.setName("HomeEssentials");
        seller.setEmail("");
        seller.setPhone("444-333-222");
        seller.setPlainPassword("password4");
        seller.setPassword("1A52A9AFD15BF15EE7E004F9730A660D");
    }

    //TODO: Quitar este
    @Test
    void testFindAllSellers() {
        List<SellerEntity> sellers = Arrays.asList(seller);
        when(sellerDAO.findAll()).thenReturn(sellers);

        List<SellerEntity> result = sellersService.findAllSellers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("HomeEssentials", result.get(0).getName());
        verify(sellerDAO, times(1)).findAll();
    }

    @Test
    void testFindSellerByCifOk() {
        when(sellerDAO.findByCif("D45678901")).thenReturn(seller);

        SellerEntity result = sellersService.findSellerByCif("D45678901");

        assertNotNull(result);
        assertEquals("HomeEssentials", result.getName());
        verify(sellerDAO, times(1)).findByCif("D45678901");
    }

    @Test
    void testFindSellerByCifNotFound() {
        when(sellerDAO.findByCif("errorCif")).thenReturn(null);

        SellerEntity result = sellersService.findSellerByCif("errorCif");

        assertNull(result);

        verify(sellerDAO, times(1)).findByCif("errorCif");
    }


    @Test
    void testUpdateSellerOk() {
        SellerEntity updatedSeller = new SellerEntity();
        updatedSeller.setCif("D45678901");
        updatedSeller.setName("new HomeEssentials");
        updatedSeller.setEmail("new@email.com");
        updatedSeller.setPhone("111-222-333");
        updatedSeller.setPlainPassword("password5");
        updatedSeller.setPassword("8B2C86EA9CF2EA4EB517FD1E06B74F399E7FEC0FEF92E3B482A6CF2E2B092023");

        when(sellerDAO.findByCif("D45678901")).thenReturn(seller);
        when(sellerDAO.save(any(SellerEntity.class))).thenReturn(updatedSeller);

        SellerEntity result = sellersService.updateSeller(updatedSeller);

        assertEquals("new HomeEssentials", result.getName());
        verify(sellerDAO, times(1)).findByCif("D45678901");
        verify(sellerDAO, times(1)).save(any(SellerEntity.class));
    }

    @Test
    void testUpdateSellerNotFound() {
        SellerEntity updatedSeller = new SellerEntity();
        updatedSeller.setCif("errorCif");
        updatedSeller.setName("notFound Seller");

        when(sellerDAO.findByCif("errorCif")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            sellersService.updateSeller(updatedSeller);
        });

        assertEquals("Seller not found with CIF: " + updatedSeller.getCif(), exception.getMessage());

        verify(sellerDAO, times(1)).findByCif("errorCif");
        verify(sellerDAO, times(0)).save(any(SellerEntity.class));
    }

    @Test
    void testUpdateSellerInvalidData() {
        SellerEntity updatedSeller = new SellerEntity();
        updatedSeller.setCif("D45678901");
        updatedSeller.setName("");
        updatedSeller.setEmail("invalid-email");

        when(sellerDAO.findByCif("D45678901")).thenReturn(seller);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sellersService.updateSeller(updatedSeller);
        });

        assertEquals("Invalid seller data", exception.getMessage());

        verify(sellerDAO, times(1)).findByCif("D45678901");
        verify(sellerDAO, times(0)).save(any(SellerEntity.class));
    }

}
