import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyRoleDetailComponent } from 'app/entities/party-role/party-role-detail.component';
import { PartyRole } from 'app/shared/model/party-role.model';

describe('Component Tests', () => {
  describe('PartyRole Management Detail Component', () => {
    let comp: PartyRoleDetailComponent;
    let fixture: ComponentFixture<PartyRoleDetailComponent>;
    const route = ({ data: of({ partyRole: new PartyRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyRole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
