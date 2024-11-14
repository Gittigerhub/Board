package com.example.boardproj.config;

import com.example.boardproj.dto.BoardDTO;
import org.modelmapper.ModelMapper;

public class CustomModelMapper extends ModelMapper {

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
//        기존에 사용하던 방식인 아래 코드를 재정의 한다.
//        BoardDTO boardDTO =
//                ModelMapper.map(board, BoardDTO.class);
        if (source == null) {
            return null;
        }

        return super.map(source, destinationType);

    }
}
