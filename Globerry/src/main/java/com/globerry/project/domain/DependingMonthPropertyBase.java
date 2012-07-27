/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import com.globerry.project.domain.Month;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Index;

/**
 *
 * @author max
 */
@Entity
@Table(name = "DependingMonthProperty")
@org.hibernate.annotations.Table(appliesTo = "DependingMonthProperty", indexes = { 
                                                                                @Index(name="JANUARY-idx", 
                                                                                        columnNames = { 
                                                                                                "JANUARY_value_left",
                                                                                                "JANUARY_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="FEBRUARY-idx", 
                                                                                        columnNames = { 
                                                                                                "FEBRUARY_value_left",
                                                                                                "FEBRUARY_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="MARCH-idx", 
                                                                                        columnNames = { 
                                                                                                "MARCH_value_left",
                                                                                                "MARCH_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="APRIL-idx", 
                                                                                        columnNames = { 
                                                                                                "APRIL_value_left",
                                                                                                "APRIL_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="MAY-idx", 
                                                                                        columnNames = { 
                                                                                                "MAY_value_left",
                                                                                                "MAY_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="JUNE-idx", 
                                                                                        columnNames = { 
                                                                                                "JUNE_value_left",
                                                                                                "JUNE_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="JULY-idx", 
                                                                                        columnNames = { 
                                                                                                "JULY_value_left",
                                                                                                "JULY_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="AUGUST-idx", 
                                                                                        columnNames = { 
                                                                                                "AUGUST_value_left",
                                                                                                "AUGUST_value_right",                                                                                                    
                                                                                                    }),
                                                                                @Index(name="SEPTEMBER-idx", 
                                                                                        columnNames = { 
                                                                                                "SEPTEMBER_value_left",
                                                                                                "SEPTEMBER_value_right",                                                                                                    
                                                                                                    }),
                                                                                 @Index(name="OCTOBER-idx", 
                                                                                        columnNames = { 
                                                                                                "OCTOBER_value_left",
                                                                                                "OCTOBER_value_right",                                                                                                    
                                                                                                    }),
                                                                                  @Index(name="NOVEMBER-idx", 
                                                                                        columnNames = { 
                                                                                                "NOVEMBER_value_left",
                                                                                                "NOVEMBER_value_right",                                                                                                    
                                                                                                    }),
                                                                                   @Index(name="DECEMBER-idx", 
                                                                                        columnNames = { 
                                                                                                "DECEMBER_value_left",
                                                                                                "DECEMBER_value_right",                                                                                                    
                                                                                                    }),

                                                                                        })
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PROPERTY_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class DependingMonthPropertyBase implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="JANUARY_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="JANUARY_value_right") )
    } )
    private Interval JANUARYValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="FEBRUARY_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="FEBRUARY_value_right") )
    } )
    private Interval FEBRUARYValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="MARCH_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="MARCH_value_right") )
    } )
    private Interval MARCHValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="APRIL_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="APRIL_value_right") )
    } )
    private Interval APRILValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="MAY_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="MAY_value_right") )
    } )
    private Interval MAYValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="JUNE_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="JUNE_value_right") )
    } )
    private Interval JUNEValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="JULY_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="JULY_value_right") )
    } )
    private Interval JULYValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="AUGUST_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="AUGUST_value_right") )
    } )
    private Interval AUGUSTValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="SEPTEMBER_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="SEPTEMBER_value_right") )
    } )
    private Interval SEPTEMBERValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="OCTOBER_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="OCTOBER_value_right") )
    } )
    private Interval OCTOBERValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="NOVEMBER_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="NOVEMBER_value_right") )
    } )
    private Interval NOVEMBERValue = new Interval();
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="DECEMBER_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="DECEMBER_value_right") )
    } )
    private Interval DECEMBERValue = new Interval();
    
    public Interval getValue(Month month)
    {
        
        switch (month.ordinal())
        {
            case 0:     return JANUARYValue;
            case 1:     return FEBRUARYValue;
            case 2:     return MARCHValue;
            case 3:     return APRILValue;
            case 4:     return MAYValue;
            case 5:     return JUNEValue;
            case 6:     return JULYValue;
            case 7:     return AUGUSTValue;
            case 8:     return SEPTEMBERValue;
            case 9:     return OCTOBERValue;
            case 10:    return NOVEMBERValue;
            case 11:    return DECEMBERValue;
        }
        return null;
    }
    
    public void setValue(Month month, Interval value)
    {
        
        switch (month.ordinal())
        {
            case 0:  JANUARYValue   = value;
            case 1:  FEBRUARYValue  = value;
            case 2:  MARCHValue     = value;
            case 3:  APRILValue     = value;
            case 4:  MAYValue       = value;
            case 5:  JUNEValue      = value;
            case 6:  JULYValue      = value;
            case 7:  AUGUSTValue    = value;
            case 8:  SEPTEMBERValue = value;
            case 9:  OCTOBERValue   = value;
            case 10: NOVEMBERValue  = value;
            case 11: DECEMBERValue  = value;
        }       
    }
    
    public DependingMonthPropertyBase()
    {
        
    }
    
    public void init(Interval[] values)
    {
        for(int i = 0; i < 12; i++)
            setValue(Month.values()[i], values[i]);
    }

    /**
     * @return the JANUARYValue
     */
    public Interval getJANUARYValue()
    {
        return JANUARYValue;
    }

    /**
     * @param JANUARYValue the JANUARYValue to set
     */
    public void setJANUARYValue(Interval JANUARYValue)
    {
        this.JANUARYValue = JANUARYValue;
    }

    /**
     * @return the FEBRUARYValue
     */
    public Interval getFEBRUARYValue()
    {
        return FEBRUARYValue;
    }

    /**
     * @param FEBRUARYValue the FEBRUARYValue to set
     */
    public void setFEBRUARYValue(Interval FEBRUARYValue)
    {
        this.FEBRUARYValue = FEBRUARYValue;
    }

    /**
     * @return the MARCHValue
     */
    public Interval getMARCHValue()
    {
        return MARCHValue;
    }

    /**
     * @param MARCHValue the MARCHValue to set
     */
    public void setMARCHValue(Interval MARCHValue)
    {
        this.MARCHValue = MARCHValue;
    }

    /**
     * @return the APRILValue
     */
    public Interval getAPRILValue()
    {
        return APRILValue;
    }

    /**
     * @param APRILValue the APRILValue to set
     */
    public void setAPRILValue(Interval APRILValue)
    {
        this.APRILValue = APRILValue;
    }

    /**
     * @return the MAYValue
     */
    public Interval getMAYValue()
    {
        return MAYValue;
    }

    /**
     * @param MAYValue the MAYValue to set
     */
    public void setMAYValue(Interval MAYValue)
    {
        this.MAYValue = MAYValue;
    }

    /**
     * @return the JUNEValue
     */
    public Interval getJUNEValue()
    {
        return JUNEValue;
    }

    /**
     * @param JUNEValue the JUNEValue to set
     */
    public void setJUNEValue(Interval JUNEValue)
    {
        this.JUNEValue = JUNEValue;
    }

    /**
     * @return the JULYValue
     */
    public Interval getJULYValue()
    {
        return JULYValue;
    }

    /**
     * @param JULYValue the JULYValue to set
     */
    public void setJULYValue(Interval JULYValue)
    {
        this.JULYValue = JULYValue;
    }

    /**
     * @return the AUGUSTValue
     */
    public Interval getAUGUSTValue()
    {
        return AUGUSTValue;
    }

    /**
     * @param AUGUSTValue the AUGUSTValue to set
     */
    public void setAUGUSTValue(Interval AUGUSTValue)
    {
        this.AUGUSTValue = AUGUSTValue;
    }

    /**
     * @return the SEPTEMBERValue
     */
    public Interval getSEPTEMBERValue()
    {
        return SEPTEMBERValue;
    }

    /**
     * @param SEPTEMBERValue the SEPTEMBERValue to set
     */
    public void setSEPTEMBERValue(Interval SEPTEMBERValue)
    {
        this.SEPTEMBERValue = SEPTEMBERValue;
    }

    /**
     * @return the OCTOBERValue
     */
    public Interval getOCTOBERValue()
    {
        return OCTOBERValue;
    }

    /**
     * @param OCTOBERValue the OCTOBERValue to set
     */
    public void setOCTOBERValue(Interval OCTOBERValue)
    {
        this.OCTOBERValue = OCTOBERValue;
    }

    /**
     * @return the NOVEMBERValue
     */
    public Interval getNOVEMBERValue()
    {
        return NOVEMBERValue;
    }

    /**
     * @param NOVEMBERValue the NOVEMBERValue to set
     */
    public void setNOVEMBERValue(Interval NOVEMBERValue)
    {
        this.NOVEMBERValue = NOVEMBERValue;
    }

    /**
     * @return the DECEMBERValue
     */
    public Interval getDECEMBERValue()
    {
        return DECEMBERValue;
    }

    /**
     * @param DECEMBERValue the DECEMBERValue to set
     */
    public void setDECEMBERValue(Interval DECEMBERValue)
    {
        this.DECEMBERValue = DECEMBERValue;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }    
    
    @Override
    public String toString()
    {
        String str = this.getClass().getName() + ": \n";        
        for(Month month: Month.values())
            str += "    " + month.toString() + ": " +  getValue(month).toString() + "\n";
        return str;
    }
}
