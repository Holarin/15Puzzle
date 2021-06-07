package sample;


public class Timer extends Thread {
    @Override
    public void run() {
        while (Controller.inGame) {
            try {
                sleep(100);
                Controller.time += 0.1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
