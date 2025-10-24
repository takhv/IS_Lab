export interface HumanBeing {
  id: number;
  name: string;
  x: number;
  y: number;
  realHero: boolean;
  hasToothpick: boolean;
  carCool: boolean;
  carType: string;
  mood: string;
  impactSpeed: number;
  soundtrackName: string;
  weaponType: string;
  creationDate: string;
}

export interface HumanBeingCreate {
  name: string;
  x: number;
  y: number;
  realHero: boolean;
  hasToothpick?: boolean;
  carCool?: boolean;
  carType?: string;
  mood?: string;
  impactSpeed: number;
  soundtrackName: string;
  weaponType: string;
}

export interface HumanBeingUpdate extends HumanBeingCreate {
  id: number;
}