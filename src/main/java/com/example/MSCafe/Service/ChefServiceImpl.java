package com.example.MSCafe.Service;

import com.example.MSCafe.exception.ChefNotFoundException;
import com.example.MSCafe.model.Account;
import com.example.MSCafe.model.Chef;
import com.example.MSCafe.Repository.AccountRepository;
import com.example.MSCafe.Repository.ChefRepository;
import com.example.MSCafe.dto.request.ChefRequestDto;
import com.example.MSCafe.dto.response.ChefResponseDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class ChefServiceImpl implements ChefService{
    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ChefResponseDto registerChef(ChefRequestDto chefRequestDto) {
       Chef chef = new Chef();
       chef.setName(chefRequestDto.getName());
       chef.setExperience(chefRequestDto.getExperience());
       chef.setCuisine(chefRequestDto.getCuisine());

        Account account = new Account();
//        accountRepository.save(account);
        account.setChef(chef);
        chef.setAccount(account);

       chefRepository.save(chef);

//       account.setChef(chef);
//       accountRepository.save(account);

       ChefResponseDto chefResponseDto = new ChefResponseDto();

        return mapChefToChefResponseDto(chef);
    }

    @Override
    public ChefResponseDto getChef(Long id) {
        Chef chef = chefRepository.findById(id).orElseThrow( () -> new ChefNotFoundException("Chef id: " +id+ " Doesn't exist") );

        ChefResponseDto chefResponseDto = new ChefResponseDto();

        chefResponseDto.setId(chef.getId());
        chefResponseDto.setName(chef.getName());
        chefResponseDto.setExperience(chef.getExperience());
        chefResponseDto.setCuisine(chef.getCuisine());
        chefResponseDto.setAccount(chef.getAccount());

        return chefResponseDto;
    }

    @Override
    public List<ChefResponseDto> getAllChef() {
        List<Chef> chefList = new LinkedList<>(chefRepository.findAll());
        List<ChefResponseDto> chefResponseDtoList = new LinkedList<>();

        for(Chef chef : chefList) {
            chefResponseDtoList.add(mapChefToChefResponseDto(chef));
        }
        return chefResponseDtoList;
    }

    @Override
    public ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto) {
        Chef chef = chefRepository.findById(id).orElse(null);
        chef.setName(chefRequestDto.getName());
        chef.setExperience(chefRequestDto.getExperience());
        chef.setCuisine(chefRequestDto.getCuisine());

        chefRepository.save(chef);

        return mapChefToChefResponseDto(chef);
    }

    @Override
    public GenericResponseDto removeChef(Long id) {
        Chef chef = chefRepository.findById(id).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        if (chef != null ) {
            chefRepository.deleteById(id);
            String name = chef.getName();
            String massage = " Chef name : " + name + "(" +id+ ") has been remove";

            genericResponseDto.setSuccess(true);
            genericResponseDto.setMessage(massage);
        }else {
            genericResponseDto.setSuccess(false);
            genericResponseDto.setMessage("Chef id: " +id+" Not Found");
        }
        return genericResponseDto;
    }

    @Override
    public Page<ChefResponseDto> getChefPage(Integer pageIndex, Integer pageSize, String sortByAttribute, String sortInOrder) {
        Sort sort = (sortInOrder.equalsIgnoreCase("desc")) ? Sort.by(sortByAttribute).descending() : Sort.by(sortByAttribute).ascending();

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<Chef> chefPage = chefRepository.findAll(pageable);

        Page<ChefResponseDto> chefResponseDtoPage = chefPage.map(chef -> mapChefToChefResponseDto(chef));

        return chefResponseDtoPage;
    }

    // Helper methods

    // Map Chef to ChefResponseDto
    public ChefResponseDto mapChefToChefResponseDto(Chef chef) {
        ChefResponseDto chefResponseDto = new ChefResponseDto();

        chefResponseDto.setId(chef.getId());
        chefResponseDto.setName(chef.getName());
        chefResponseDto.setExperience(chef.getExperience());
        chefResponseDto.setCuisine(chef.getCuisine());
        chefResponseDto.setAccount(chef.getAccount());

        return chefResponseDto;

    }
}
