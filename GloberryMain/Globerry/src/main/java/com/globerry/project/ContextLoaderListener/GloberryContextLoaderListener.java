/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;

/**
 *
 * @author Max
 */
public class GloberryContextLoaderListener extends ContextLoaderListener
{

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        System.err.println("PPPPPPPPPPPPPPPPPPPPPPPPPPFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPAAAAAAAAAAAAA");
        super.contextInitialized(event);
    }
    
}
