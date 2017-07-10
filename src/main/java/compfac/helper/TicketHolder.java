package compfac.helper;

import java.util.concurrent.ConcurrentHashMap;

import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TicketHolder {
	
	private ConcurrentHashMap<Integer, Ticket> tickets = new ConcurrentHashMap<Integer, Ticket>();
	
	public TicketHolder(){}

	// Singleton Holders and their logic
	private static class L1SingletonHolder {
        static final TicketHolder INSTANCE = new TicketHolder();
    }
	private static class L2SingletonHolder {
        static final TicketHolder INSTANCE = new TicketHolder();
    }
	private static class L3SingletonHolder {
        static final TicketHolder INSTANCE = new TicketHolder();
    }

    private static TicketHolder getL1Instance() {
        return L1SingletonHolder.INSTANCE;
    }
    private static TicketHolder getL2Instance() {
        return L2SingletonHolder.INSTANCE;
    }
    private static TicketHolder getL3Instance() {
        return L3SingletonHolder.INSTANCE;
    }
    
    private void addTicket(int id, Ticket tic){
    	this.tickets.put(id, tic);
    }
    
    private Ticket getTicket(int id){
    	return this.tickets.get(id);
    }
    
    private void releaseTicket(int id){
    	this.tickets.remove(id);
    }
    
    public static void addTicket(int facLvl, int id, Ticket tic){
    	switch(facLvl){
    	case 1:
    		TicketHolder.getL1Instance().addTicket(id, tic);
    		break;
    	case 2:
    		TicketHolder.getL2Instance().addTicket(id, tic);
    		break;
    	case 3:
    		TicketHolder.getL3Instance().addTicket(id, tic);
    		break;
    	}
    }
    
    public static Ticket getTicket(int facLvl, int id){
    	Ticket ticket;
    	switch(facLvl){
    	case 1:
    		return TicketHolder.getL1Instance().getTicket(id);
    	case 2:
    		return TicketHolder.getL2Instance().getTicket(id);
    	case 3:
    		return TicketHolder.getL3Instance().getTicket(id);
    	}
		return null;
    }
    
    public static void releaseTicket(int facLvl, int id){
    	switch(facLvl){
    	case 1:
    		TicketHolder.getL1Instance().releaseTicket(id);
    		break;
    	case 2:
    		TicketHolder.getL2Instance().releaseTicket(id);
    		break;
    	case 3:
    		TicketHolder.getL3Instance().releaseTicket(id);
    		break;
    	}
    }
}
