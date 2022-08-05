import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyContactMechDetailComponent } from 'app/entities/party-contact-mech/party-contact-mech-detail.component';
import { PartyContactMech } from 'app/shared/model/party-contact-mech.model';

describe('Component Tests', () => {
  describe('PartyContactMech Management Detail Component', () => {
    let comp: PartyContactMechDetailComponent;
    let fixture: ComponentFixture<PartyContactMechDetailComponent>;
    const route = ({ data: of({ partyContactMech: new PartyContactMech(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyContactMechDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyContactMechDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyContactMechDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyContactMech on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyContactMech).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
