package com.globerry.project.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.IOptionBlock;

@Service
@Scope("session")
public class BlockWhat extends IOptionBlock
{
    public BlockWhat(){
	//TODO придется забивать в ручную
	this.addBlockItem(new BlockItem(new Tag()));
    }
}
